pragma solidity >=0.4.22 <0.9.0;

contract Voting {

    struct Candidate {
        uint id;
        string name;
        uint voteCount;
    }

    struct Voter {
        bool hasVoted;
        uint candidateIndex;
        // 추가 정보
        uint serviceId;
    }

    address public owner;
    Candidate[] public candidates;
    mapping(address => Voter) public addressToVoters;
    mapping(uint => Voter) public idToVoters;

    uint public startTime;
    uint public endTime;

    uint public totalVotes;

    event Voted(address indexed voter, uint indexed candidateIndex, string candidateName, uint serviceId);

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can perform this action");
        _;
    }

    modifier votingOpen() {
        require(block.timestamp >= startTime && block.timestamp <= endTime, "Voting is not open");
        _;
    }

    constructor(string[] memory _candidateNames, uint start, uint end) {
        owner = msg.sender;
        startTime = start;
        endTime = end;

        for (uint i = 0; i < _candidateNames.length; i++) {
            candidates.push(Candidate({
                id: i,
                name: _candidateNames[i],
                voteCount: 0
            }));
        }
    }

    function vote(uint candidateIndex, uint serviceId) public votingOpen {
        require(!addressToVoters[msg.sender].hasVoted, "Vote Already Done");
        require(!idToVoters[serviceId].hasVoted, "Vote Already Done");
        require(candidateIndex < candidates.length && candidateIndex >= 0, "Wrong Candidate ID");

        candidates[candidateIndex].voteCount += 1;
        totalVotes++;
        Voter memory voter = Voter(true, candidateIndex, serviceId);
        addressToVoters[msg.sender] = voter;
        idToVoters[serviceId] = voter;

        emit Voted(msg.sender, candidateIndex, candidates[candidateIndex].name, serviceId);
    }

    function getCandidates() public view returns (Candidate[] memory) {
        return candidates;
    }

    function getTotalVotes() public view returns (uint) {
        return totalVotes;
    }

    function hasAddressVoted(address voter) public view returns (bool) {
        return addressToVoters[voter].hasVoted;
    }

    function getVoterDetailsByAddress(address voter) public view returns (bool hasVoted, uint candidateIndex) {
        Voter memory voterInfo = addressToVoters[voter];
        return (voterInfo.hasVoted, voterInfo.candidateIndex);
    }

    function hasIdVoted(uint voter) public view returns (bool) {
        return idToVoters[voter].hasVoted;
    }

    function getVoterDetailsById(uint voter) public view returns (bool hasVoted, uint candidateIndex) {
        Voter memory voterInfo = idToVoters[voter];
        return (voterInfo.hasVoted, voterInfo.candidateIndex);
    }

    function isVotingOpen() public view returns (bool) {
        return block.timestamp >= startTime && block.timestamp <= endTime;
    }

    function updateVotingTimes(uint newStartTime, uint newEndTime) public onlyOwner {
        require(newStartTime < newEndTime, "Start time must be before end time");
        require(block.timestamp < startTime || block.timestamp > endTime, "Cannot change time while voting is open");

        startTime = newStartTime;
        endTime = newEndTime;
    }
}