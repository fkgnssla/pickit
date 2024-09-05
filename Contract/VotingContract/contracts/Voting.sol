pragma solidity >=0.4.22 <0.9.0;

contract Voting {

    struct Candidate {
        string name;
        uint256 voteCount;
    }

    address public owner;
    Candidate[] public candidates;
    mapping(address => bool) public hasVoted;

    event Voted(address indexed voter, uint indexed candidateIndex);

    constructor(string[] memory _candidateNames) {
        owner = msg.sender;
        for (uint i = 0; i < _candidateNames.length; i++) {
            candidates.push(Candidate({
                name: _candidateNames[i],
                voteCount: 0
            }));
        }
    }

    function vote(uint candidateIndex) public {
        require(!hasVoted[msg.sender], "Vote Already Done");
        require(candidateIndex < candidates.length && candidateIndex >= 0, "Wrong Candidate ID");

        candidates[candidateIndex].voteCount += 1;
        hasVoted[msg.sender] = true;

        emit Voted(msg.sender, candidateIndex);
    }

    function getCandidates() public view returns (Candidate[] memory) {
        return candidates;
    }

    function getTotalVotes() public view returns (uint256) {
        uint ret = 0;
        for (uint i=0; i< candidates.length; i++){
            ret += candidates[i].voteCount;
        }
        return ret;
    }

    function hasAddressVoted(address voter) public view returns (bool) {
        return hasVoted[voter];
    }
}