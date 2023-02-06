import React from "react";
import styled from "styled-components";

const StyledIButton = styled.button`
    margin-top: 0px;
    font-size: 70px;
    font-weight: 600;
    font-family: "HancomMalangMalang-Bold";
    border-width: 4px;
    border-radius: 50px;
    border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
    cursor: pointer;
    width: 291px;
    height: 122px;
    margin: 0 auto; /* 페이지 중앙에 나타나도록 설정 */
    color:#F64177;
    `;
    

function Button(props) {
    const { title, onClick } = props;

    return <StyledIButton onClick={onClick}>{title || "button"}</StyledIButton>;
}

export default Button;
