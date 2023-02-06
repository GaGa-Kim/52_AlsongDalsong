import React from "react";
import styled from "styled-components";

const StyledButton = styled.button`
    padding: 8px 16px;
    font-size: 14px;
    font-weight: bold;
    font-family: 'GmarketSansTTFMedium';
    border-width: 1px;
    border-radius: 10px;
    border-color:transparent;
    cursor: pointer;
    background: #FFFFFF;
    box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.25);

`;

function Button(props) {
    const { title, onClick } = props;

    return <StyledButton onClick={onClick}>{title || "button"}</StyledButton>;
}

export default Button;
