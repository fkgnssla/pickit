package com.ssafy.validate.dto;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.EthLog.LogResult;
import org.web3j.protocol.core.methods.response.Log;

public record PickitLog(
	BigInteger logIndex,
	boolean removed,
	BigInteger blockNumber,
	String blockHash,
	String transactionHash,
	BigInteger transactionIndex,
	String address,
	Map<String, Object> returnValues,
	String event
) {

	public static PickitLog toLog(LogResult logResult) {
		// Cast the LogResult object to an EthLog object
		Log ethLog = (Log) logResult.get();

		Event VOTED_EVENT = new Event("Voted",
			Arrays.asList(
				new TypeReference<Address>() {},  // voter
				new TypeReference<Uint256>() {},  // candidateIndex
				new TypeReference<Utf8String>() {},  // candidateName
				new TypeReference<Uint256>() {}   // serviceId
			)
		);

		List<Type> decodedValues = FunctionReturnDecoder.decode(
			ethLog.getData(),
			VOTED_EVENT.getParameters()
		);

		Map<String, Object> returnValues = new HashMap<>();
		returnValues.put("voter", decodedValues.get(0).getValue());
		returnValues.put("candidateIndex", decodedValues.get(1).getValue());
		returnValues.put("candidateName", decodedValues.get(2).getValue());
		returnValues.put("serviceId", decodedValues.get(3).getValue());

		return new PickitLog(
			ethLog.getLogIndex(),
			ethLog.isRemoved(),
			ethLog.getBlockNumber(),
			ethLog.getBlockHash(),
			ethLog.getTransactionHash(),
			ethLog.getTransactionIndex(),
			ethLog.getAddress(),
			returnValues,
			ethLog.getType()
		);
	}
}
