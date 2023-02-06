import React from "react";
import user from "../ui/Userimg.png";
import styled from "styled-components";

const StyledButton = styled.button`
  position: absolute;
  left: 310px;
  top: 23px;
  cursor: pointer;
  background-color : #FA0050;
  border : 0px;
  border-radius: 16px;

`;

function Button(props) {
    const { title, onClick } = props;

    return <StyledButton onClick={onClick} ><img src={user} alt="Userimg" /></StyledButton>
}

export default Button;
