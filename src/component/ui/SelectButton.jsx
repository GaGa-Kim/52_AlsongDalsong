import React from "react";
import styled from "styled-components";

const StyledButton = styled.button`
    text-align: center;
    font-size: 25px;
    font-weight: 600;
    font-family: 'GmarketSansTTFBold';
    border-width: 4px;
    border-radius: 50px;
    border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
    width: 173.21px;
    height: 57.65px;
    color: #940F00;
    background-color: white;
    margin-top: 5px;
    margin: 0 auto;
    `;


function Button(props) {
    const { title, onClick } = props;

    return <StyledButton onClick={onClick}>{title || "button"}</StyledButton>;
}

export default Button;