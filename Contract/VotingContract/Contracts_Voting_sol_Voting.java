package com.ssafy.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class Contracts_Voting_sol_Voting extends Contract {
    public static final String BINARY = "[\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"string[]\",\r\n"
            + "          \"name\": \"_candidateNames\",\r\n"
            + "          \"type\": \"string[]\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"start\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"end\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"nonpayable\",\r\n"
            + "      \"type\": \"constructor\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"anonymous\": false,\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"indexed\": true,\r\n"
            + "          \"internalType\": \"address\",\r\n"
            + "          \"name\": \"voter\",\r\n"
            + "          \"type\": \"address\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"indexed\": true,\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"candidateIndex\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"indexed\": false,\r\n"
            + "          \"internalType\": \"string\",\r\n"
            + "          \"name\": \"candidateName\",\r\n"
            + "          \"type\": \"string\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"indexed\": false,\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"serviceId\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"Voted\",\r\n"
            + "      \"type\": \"event\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"address\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"address\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"addressToVoters\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"hasVoted\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"candidateIndex\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"serviceId\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"candidates\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"id\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"string\",\r\n"
            + "          \"name\": \"name\",\r\n"
            + "          \"type\": \"string\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"voteCount\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"endTime\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"idToVoters\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"hasVoted\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"candidateIndex\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"serviceId\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"owner\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"address\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"address\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"startTime\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"totalVotes\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"candidateIndex\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"serviceId\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"vote\",\r\n"
            + "      \"outputs\": [],\r\n"
            + "      \"stateMutability\": \"nonpayable\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"getCandidates\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"components\": [\r\n"
            + "            {\r\n"
            + "              \"internalType\": \"uint256\",\r\n"
            + "              \"name\": \"id\",\r\n"
            + "              \"type\": \"uint256\"\r\n"
            + "            },\r\n"
            + "            {\r\n"
            + "              \"internalType\": \"string\",\r\n"
            + "              \"name\": \"name\",\r\n"
            + "              \"type\": \"string\"\r\n"
            + "            },\r\n"
            + "            {\r\n"
            + "              \"internalType\": \"uint256\",\r\n"
            + "              \"name\": \"voteCount\",\r\n"
            + "              \"type\": \"uint256\"\r\n"
            + "            }\r\n"
            + "          ],\r\n"
            + "          \"internalType\": \"struct Voting.Candidate[]\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"tuple[]\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"getTotalVotes\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"address\",\r\n"
            + "          \"name\": \"voter\",\r\n"
            + "          \"type\": \"address\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"hasAddressVoted\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"address\",\r\n"
            + "          \"name\": \"voter\",\r\n"
            + "          \"type\": \"address\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"getVoterDetailsByAddress\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"hasVoted\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"candidateIndex\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"voter\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"hasIdVoted\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"voter\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"getVoterDetailsById\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"hasVoted\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"candidateIndex\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [],\r\n"
            + "      \"name\": \"isVotingOpen\",\r\n"
            + "      \"outputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"bool\",\r\n"
            + "          \"name\": \"\",\r\n"
            + "          \"type\": \"bool\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"stateMutability\": \"view\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    },\r\n"
            + "    {\r\n"
            + "      \"inputs\": [\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"newStartTime\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "          \"internalType\": \"uint256\",\r\n"
            + "          \"name\": \"newEndTime\",\r\n"
            + "          \"type\": \"uint256\"\r\n"
            + "        }\r\n"
            + "      ],\r\n"
            + "      \"name\": \"updateVotingTimes\",\r\n"
            + "      \"outputs\": [],\r\n"
            + "      \"stateMutability\": \"nonpayable\",\r\n"
            + "      \"type\": \"function\"\r\n"
            + "    }\r\n"
            + "  ]";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDRESSTOVOTERS = "addressToVoters";

    public static final String FUNC_CANDIDATES = "candidates";

    public static final String FUNC_ENDTIME = "endTime";

    public static final String FUNC_IDTOVOTERS = "idToVoters";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_STARTTIME = "startTime";

    public static final String FUNC_TOTALVOTES = "totalVotes";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_GETCANDIDATES = "getCandidates";

    public static final String FUNC_GETTOTALVOTES = "getTotalVotes";

    public static final String FUNC_HASADDRESSVOTED = "hasAddressVoted";

    public static final String FUNC_GETVOTERDETAILSBYADDRESS = "getVoterDetailsByAddress";

    public static final String FUNC_HASIDVOTED = "hasIdVoted";

    public static final String FUNC_GETVOTERDETAILSBYID = "getVoterDetailsById";

    public static final String FUNC_ISVOTINGOPEN = "isVotingOpen";

    public static final String FUNC_UPDATEVOTINGTIMES = "updateVotingTimes";

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Contracts_Voting_sol_Voting(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contracts_Voting_sol_Voting(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Contracts_Voting_sol_Voting(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Contracts_Voting_sol_Voting(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.candidateIndex = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.candidateName = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serviceId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotedEventResponse getVotedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTED_EVENT, log);
        VotedEventResponse typedResponse = new VotedEventResponse();
        typedResponse.log = log;
        typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.candidateIndex = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.candidateName = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.serviceId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<VotedEventResponse> votedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotedEventFromLog(log));
    }

    public Flowable<VotedEventResponse> votedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple3<Boolean, BigInteger, BigInteger>> addressToVoters(
            String param0) {
        final Function function = new Function(FUNC_ADDRESSTOVOTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<Boolean, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<Boolean, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<Boolean, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<Boolean, BigInteger, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<BigInteger, String, BigInteger>> candidates(
            BigInteger param0) {
        final Function function = new Function(FUNC_CANDIDATES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, String, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> endTime() {
        final Function function = new Function(FUNC_ENDTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<Boolean, BigInteger, BigInteger>> idToVoters(
            BigInteger param0) {
        final Function function = new Function(FUNC_IDTOVOTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<Boolean, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<Boolean, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<Boolean, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<Boolean, BigInteger, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> startTime() {
        final Function function = new Function(FUNC_STARTTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalVotes() {
        final Function function = new Function(FUNC_TOTALVOTES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger candidateIndex,
            BigInteger serviceId) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(candidateIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(serviceId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getCandidates() {
        final Function function = new Function(FUNC_GETCANDIDATES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Candidate>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getTotalVotes() {
        final Function function = new Function(FUNC_GETTOTALVOTES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> hasAddressVoted(String voter) {
        final Function function = new Function(FUNC_HASADDRESSVOTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, voter)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple2<Boolean, BigInteger>> getVoterDetailsByAddress(String voter) {
        final Function function = new Function(FUNC_GETVOTERDETAILSBYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, voter)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, BigInteger>>(function,
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> hasIdVoted(BigInteger voter) {
        final Function function = new Function(FUNC_HASIDVOTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(voter)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple2<Boolean, BigInteger>> getVoterDetailsById(BigInteger voter) {
        final Function function = new Function(FUNC_GETVOTERDETAILSBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(voter)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, BigInteger>>(function,
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isVotingOpen() {
        final Function function = new Function(FUNC_ISVOTINGOPEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateVotingTimes(BigInteger newStartTime,
            BigInteger newEndTime) {
        final Function function = new Function(
                FUNC_UPDATEVOTINGTIMES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newStartTime), 
                new org.web3j.abi.datatypes.generated.Uint256(newEndTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Contracts_Voting_sol_Voting load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_Voting_sol_Voting(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Contracts_Voting_sol_Voting load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_Voting_sol_Voting(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Contracts_Voting_sol_Voting load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Contracts_Voting_sol_Voting(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Contracts_Voting_sol_Voting load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Contracts_Voting_sol_Voting(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Contracts_Voting_sol_Voting> deploy(Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider,
            List<String> _candidateNames, BigInteger start, BigInteger end) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_candidateNames, org.web3j.abi.datatypes.Utf8String.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(start), 
                new org.web3j.abi.datatypes.generated.Uint256(end)));
        return deployRemoteCall(Contracts_Voting_sol_Voting.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<Contracts_Voting_sol_Voting> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            List<String> _candidateNames, BigInteger start, BigInteger end) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_candidateNames, org.web3j.abi.datatypes.Utf8String.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(start), 
                new org.web3j.abi.datatypes.generated.Uint256(end)));
        return deployRemoteCall(Contracts_Voting_sol_Voting.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Contracts_Voting_sol_Voting> deploy(Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit,
            List<String> _candidateNames, BigInteger start, BigInteger end) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_candidateNames, org.web3j.abi.datatypes.Utf8String.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(start), 
                new org.web3j.abi.datatypes.generated.Uint256(end)));
        return deployRemoteCall(Contracts_Voting_sol_Voting.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Contracts_Voting_sol_Voting> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            List<String> _candidateNames, BigInteger start, BigInteger end) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_candidateNames, org.web3j.abi.datatypes.Utf8String.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(start), 
                new org.web3j.abi.datatypes.generated.Uint256(end)));
        return deployRemoteCall(Contracts_Voting_sol_Voting.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Candidate extends DynamicStruct {
        public BigInteger id;

        public String name;

        public BigInteger voteCount;

        public Candidate(BigInteger id, String name, BigInteger voteCount) {
            super(new org.web3j.abi.datatypes.generated.Uint256(id), 
                    new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.generated.Uint256(voteCount));
            this.id = id;
            this.name = name;
            this.voteCount = voteCount;
        }

        public Candidate(Uint256 id, Utf8String name, Uint256 voteCount) {
            super(id, name, voteCount);
            this.id = id.getValue();
            this.name = name.getValue();
            this.voteCount = voteCount.getValue();
        }
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public String voter;

        public BigInteger candidateIndex;

        public String candidateName;

        public BigInteger serviceId;
    }
}
