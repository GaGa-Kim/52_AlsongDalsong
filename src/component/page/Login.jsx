import React from "react";
import { useNavigate } from "react-router-dom";
import BButton from "../ui/loginrb";
import Template from "../ui/template";
import Button from "../ui/loginb"
import Userimg from "../ui/Userimg";
import Drop from "../ui/Drop"

function Selectpage(props) {
    const navigate = useNavigate();

    return (
      
         
        <Template>
        <Drop></Drop>
          <Userimg
             onClick={() => {
             navigate("/auth/select-page");
          }}/>
          <Button></Button>
          <BButton
                    title="룰렛 바로가기"
                    onClick={() => {
                        navigate("/wheel-page");
                    }}
                />
        </Template>
      
     
    );
}

export default Selectpage;
