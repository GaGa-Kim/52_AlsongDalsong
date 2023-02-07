import React from "react";
import styled from "styled-components";

const StyledIButton = styled.p`
    margin-top: 0px;
    font-size: 13px;
    font-weight: 100;
    font-family: 'GMarketSansTTFBold';
    border-width: 2px;
    cursor: pointer;
    top: 190px;
    margin-left: 3%;
    color:#F64177;
    width: 70px;
    height: 35px;
    padding-top: 12px;
    margin-right: 20px;
    `;
    

function button(props) {
    const { title } = props;

    return <StyledIButton>{title || "button"}</StyledIButton>;
}

export default button;