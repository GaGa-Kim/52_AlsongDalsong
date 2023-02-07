import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import SButton from "../ui/SelectButton";
import Template from "../ui/template";
import Stemplate from "../ui/Selectbox"
import B1img from "../ui/B1img";
import B2img from "../ui/B2img";
import Userimg from "../ui/Userimg";
import Drop from "../ui/Drop";




const SText = styled.p`
    font-size: 25px;
    font-weight: 900;
    font-family: 'GmarketSansTTFBold';
    text-align: center;
    color: #940F00;
    margin-top: 5%;
    margin-bottom: 3%;
    letter-spacing: 1px;
    line-height:170%;
    
`;

const Space = styled.div`
  width: 15px;
  height: 20px;
  display: inline-block;
`;


const MainTitleText = styled.p`
padding-bottom:30%
letter-spacing: 2px;
`;


function Selectpage(props) {
    const navigate = useNavigate();

    return (
      
        <Template>
          <Drop></Drop>
          <MainTitleText/>
          <Userimg
             onClick={() => {
             navigate("/post-a");
          }}/>
          <Space></Space> 
          <Space></Space> 
                <Stemplate>
                  <SText>시간이 없다면?</SText>
                  <B1img/>
                <SButton
                    title="룰렛 PICK"
                    onClick={() => {
                        navigate("/wheel-page");
                    }}
                />
                </Stemplate>
              <Stemplate>
              <SText>의견이 필요하다면?</SText>
                <B2img/>
                 <SButton
                    title="투표 받기"
                    onClick={() => {
                        navigate("/writepage");
                    }}
                />
                </Stemplate>
        </Template>
      
       
    );
}

export default Selectpage;
