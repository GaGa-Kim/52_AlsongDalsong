import React, { useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`

  flex-direction: row;
  margin-left: auto;


  .count {
    color: #434343;
    font-weight: 500;
    font-size: 13px;
    line-height: 16px;
    align-items: center;
    text-align: center;
  }
  .hearticon{
    width: 5px;
    height:5px;
    font-size: 15px;
    height: auto;
    display: inline-block;
  }
`;
const Space_1 = styled.div`
  width: 3px;
  height: auto;
  display: inline-block;
`;
function HeartButton() {
  const [heart, setHeart] = useState(0); //하트 버튼구현

  return (
    <Wrapper>
      <span
        onClick={() => {
          setHeart(heart + 1);
        }}
      >
        <i className="hearticon fa-solid fa-heart"></i>
      </span>
      <Space_1/>
      <i className="count">{heart}</i>
    </Wrapper>
  );
}

export default HeartButton;
