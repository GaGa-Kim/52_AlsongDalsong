import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Template from "../ui/template";
import Userimg from "../ui/Userimg";
import Drop from "../ui/Drop";
import "../ui/styles.css";
import DraggeableForm from "../ui/DraggeableForm";
import BTemplate from "../ui/BackgroundBox";

const Select = styled.select`

    position: flex;
    width: 300px;
    font-family: 'GmarketSansTTFBold';
    font-style: normal;
    font-weight: 700;
    font-size: 30px;
    line-height: 52px;
    text-align: center;
    margin-top: 30px;
    color: #FA0050;
    border: hidden;
    border-radius: 10px;
`;


const MainTitleText = styled.p`

margin bottom:30%
letter-spacing: 2px;
`;


function Roulettepage(props) {
    const navigate = useNavigate();

    return (
        <Template>
            <Drop/>
            <Userimg
                onClick={() => {
                navigate("/new");
            }}/>
            <BTemplate>
                <MainTitleText/>

                <Select>
                    <option key="Key1" value="key1">무엇을 먹을까?</option>
                    <option key="Key2" value="key2">어디를 갈까?</option>
                    <option key="Key3" value="key3">어디서 만날까?</option>
                </Select>
                
                <DraggeableForm />
            </BTemplate>
        </Template>
       
    );
}

export default Roulettepage;
