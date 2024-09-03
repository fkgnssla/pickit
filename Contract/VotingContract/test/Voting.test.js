const Voting = artifacts.require("Voting");

contract("Voting", accounts => {
    let votingInstance;
    const candidateNames = ["Alice", "Bob", "Charlie"];

    before(async () => {
        votingInstance = await Voting.deployed();
    });

    it("should deploy the contract successfully", async () => {
        assert(votingInstance.address !== '');
    });

    it("should initialize with correct candidates", async () => {
        const candidates = await votingInstance.getCandidates();
        assert.equal(candidates.length, candidateNames.length, "Number of candidates should match");

        for (let i = 0; i < candidateNames.length; i++) {
            assert.equal(candidates[i].name, candidateNames[i], `Candidate name should be ${candidateNames[i]}`);
            assert.equal(candidates[i].voteCount.toString(), '0', "Initial vote count should be 0");
        }
    });

    it("should allow a user to vote", async () => {
        await votingInstance.vote(0, { from: accounts[0] });
        const candidates = await votingInstance.getCandidates();
        const hasVoted = await votingInstance.hasAddressVoted(accounts[0]);

        assert.equal(candidates[0].voteCount.toString(), '1', "Vote count should be 1 after voting");
        assert.equal(hasVoted, true, "The voter should be marked as having voted");
    });

    it("should not allow double voting", async () => {
        try {
            await votingInstance.vote(0, { from: accounts[0] });
            assert.fail("Should have thrown an error when trying to vote again");
        } catch (error) {
            assert(error.message.includes("Vote Already Done"), "Expected error message for double voting");
        }
    });

    it("should not allow voting for an invalid candidate", async () => {
        try {
            await votingInstance.vote(999, { from: accounts[1] }); // Invalid candidate index
            assert.fail("Should have thrown an error for invalid candidate");
        } catch (error) {
            assert(error.message.includes("Wrong Candidate ID"), "Expected error message for invalid candidate");
        }
    });

    it("should correctly return the total number of votes", async () => {
        await votingInstance.vote(1, { from: accounts[1] });
        const totalVotes = await votingInstance.getTotalVotes();

        assert.equal(totalVotes.toString(), '2', "Total votes should be 2 after two votes");
    });

    it("should emit a Voted event when a vote is cast", async () => {
        const receipt = await votingInstance.vote(2, { from: accounts[2] });

        assert.equal(receipt.logs.length, 1, "One event should have been triggered");
        assert.equal(receipt.logs[0].event, "Voted", "Event type should be Voted");
        assert.equal(receipt.logs[0].args.voter, accounts[2], "The voter should be the third account");
        assert.equal(receipt.logs[0].args.candidateIndex.toNumber(), 2, "The candidate index should be 2");
    });
});
