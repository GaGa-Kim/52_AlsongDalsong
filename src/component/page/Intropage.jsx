import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import IButton from "../ui/IntroButton";
import Template from "../ui/template";
import Iimg from "../ui/Introimg";

const IntroText = styled.p`
    font-size: 22px;
    font-family: "HancomMalangMalang-Bold";
    text-align: center;
    color: #FFFFFF;
    margin-top: 8%;
    margin-bottom: 1%;
    letter-spacing: 2px;
    line-height:170%;
    
`;


const MainTitleText = styled.p`
    font-size: 50px;
    font-weight: 900;
    font-family: "GmarketSansTTFBold";
    text-align: center;
    color: #FFFFFF;
    letter-spacing: 2px;
`;

const Space = styled.div`
  width: 0.5px;
  height: 30px;
  display: inline-block;
`;


function Intropage(props) {
    const navigate = useNavigate();

    return (
        <Template>
           <IntroText>이거 살까 말까?<br></br>여기 갈까 말까?<br></br>이거 할까 말까?<br></br>누군가가 결정해주길 원한다면?</IntroText>
            <Space></Space>
            <MainTitleText>알송달송?!</MainTitleText>
            <Space></Space>
                <Iimg/>
                <IButton
                    title="Start"
                    onClick={() => {
                        navigate("/login");
                    }}
                />
        </Template>
    );
}

export default Intropage;
