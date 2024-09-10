from fastapi import FastAPI, HTTPException, status
from pydantic import BaseModel
import subprocess
import shlex
import uvicorn
import os

app = FastAPI()

class DeployRequest(BaseModel):
    candidate_names: list[str]

@app.post("/deployVotingContract", status_code=status.HTTP_200_OK)
async def deploy_voting_contract(request: DeployRequest):
    try:
        candidate_names = ",".join(request.candidate_names)

        current_dir = os.path.dirname(os.path.abspath(__file__))
        deploy_script_path = os.path.join(current_dir, "deploy_vote.sh")

        command = f"sh {shlex.quote(deploy_script_path)} {shlex.quote(candidate_names)}"

        result = subprocess.run(shlex.split(command), capture_output=True, text=True)

        if result.returncode != 0:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"Unknown Error : {result.stderr}"
            )

        # 출력된 스마트 컨트랙트 주소를 추출
        output = result.stdout
        match = re.search(r"Contract Address: (0x[a-fA-F0-9]{40})", output)
        if match:
            contract_address = match.group(1)
        else:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="스크립트에서 문제생김"
            )

        return {
            "status_code": status.HTTP_200_OK,
            "message": "배포 완",
            "contract_address": contract_address,
            "output": output
        }
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail="Script Error : " + str(e)
        )

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)