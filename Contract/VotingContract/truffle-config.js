const HDWalletProvider = require('@truffle/hdwallet-provider');
const mnemonic = "shield advance face twist shoulder utility help region involve need company chronic";   // Metamask의 시드 구문

module.exports = {
  networks: {
    ssafy: {
      provider: () => new HDWalletProvider(mnemonic, `https://rpc.ssafy-blockchain.com`),
      network_id: 31221,       // 네트워크 ID
      gas: 5500000,               // 가스 리밋
      confirmations: 2, // 블록 확인 대기
      timeoutBlocks: 200,         // 타임아웃 블록 수
      skipDryRun: true            // Dry run을 건너뛸지 여부
    },
  },

  compilers: {
    solc: {
      version: "0.8.19",          // Solidity 버전 설정
    }
  }
};
