import React from "react";
import styled from "styled-components";

const StyledButton = styled.button`
 
    width: 300px;
    text-align: left;
    padding: 8px 16px;
    font-size: 14px;
    font-weight: bold;
    font-size: 12px;
    font-family: 'GmarketSansTTFMedium';
    line-height: 14px;
    border-width: 1px;
    border-radius: 10px;
    border-color:transparent;
    cursor: pointer;
    background: #EFEFEF;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 15px;
    
`;

function Button(props) {
    const { title, onClick } = props;

    return <StyledButton onClick={onClick}>{title || "button"}</StyledButton>;
}

export default Button;