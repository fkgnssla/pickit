package com.ssafy.validate.dto;

import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;

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

		// Create a returnValues map from the topics and data of the log
		Map<String, Object> returnValues = new HashMap<>();
		returnValues.put("topics", ethLog.getTopics());
		returnValues.put("data", ethLog.getData());

		// Create and return a new PickitLog record
		return new PickitLog(
			ethLog.getLogIndex(), // logIndex as int
			ethLog.isRemoved(),                      // removed
			ethLog.getBlockNumber(), // blockNumber as int
			ethLog.getBlockHash(),                   // blockHash
			ethLog.getTransactionHash(),             // transactionHash
			ethLog.getTransactionIndex(), // transactionIndex as int
			ethLog.getAddress(),                     // address
			returnValues,                            // returnValues (topics and data)
			ethLog.getType()                         // event (or type)
		);
	}
}
