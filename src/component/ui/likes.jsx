import React, { useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 300px;
  height: 20px;
  flex-direction: row;
  padding-top: 0px;
  padding-right: 0px;
  padding-bottom: 15px;
  padding-left: 0px;
  margin: 0 auto;
  display: block;
 
  .upicon{
    color: #434343;
    font-size: 15px;
  }
  .downicon{
    color: #434343;
    font-size: 15px;
  }
  .cmicon{
    color: #434343;
    font-size: 15px;
  }
  .staricon{
    color: #434343;
    font-size: 15px;
  }
  .count{
    color: #434343;
    font-weight: 500;
    font-size: 12px;
    line-height: 16px;
    align-items: center;
    text-align: center;
  }
`;
const Space_2 = styled.div`
  width: 1px;
  height: auto;
  display: inline-block;
`;

function Likes() {
  const [thumbup, setThumbUp] = useState(0); //좋아요 버튼구현
  const [thumbdown, setThumbDown] = useState(0); //좋아요 버튼구현
  const [msg, setMsg] = useState(0); //좋아요 버튼구현
  const [star, setStar] = useState(0); //좋아요 버튼구현

  return (
    <Wrapper>
      <span
        onClick={() => {
            setThumbUp(thumbup + 1);
        }}
      >
        <i className="upicon fa-regular fa-thumbs-up"></i>
      </span>
      <i className="count">{thumbup}</i>
     <Space_2/>
     
      <span
        onClick={() => {
            setThumbDown(thumbdown + 1);
        }}
      >
        <i className="downicon fa-regular fa-thumbs-down"></i>
      </span>
      <i className="count">{thumbdown}</i>
      <Space_2/>
      
      <span
        onClick={() => {
            setMsg(msg + 1);
        }}
      >
        <i className="cmicon fa-regular fa-comment-dots"></i>
      </span>
      <i className="count">{msg}</i>
      <Space_2/>
      
      <span
        onClick={() => {
            setStar(star + 1);
        }}
      >
        <i className="staricon fa-regular fa-star"></i>
      </span>
      <i className="count">{star}</i>
    </Wrapper>
  );
}

export default Likes;