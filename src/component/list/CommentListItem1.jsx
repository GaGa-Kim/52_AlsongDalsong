import React, { useEffect, useState } from 'react';
import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";
import HButton from "../ui/HeartButton";
import axios from 'axios';

const Wrapper = styled.div`
  width: calc(100% - 32px);
  padding: 0px 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  border: transper;
  border-radius: 8px;
  cursor: pointer;
  background: #f9f9f9;
  :hover {
    background: lightgrey;
  }
`;

const Wrapper_2 = styled.div`
  display: flex;
  flex-direction: row;
  .usericon {
    padding-top: 10px;
    padding-botton: 10px;
    font-size: 30px;
    color: #d9d9d9;
  }
  justify-content: space-between;
  
`

const Wrapper_3=styled.div`
  margin-top: -7px;
  margin-left: auto;
`;

const ContentText = styled.p`
  font-size: 13px;
  white-space: pre-wrap;
  text-align: left;
  font-style: normal;
  font-weight: 500;
  line-height: 1.5;
  color:#000000;
  
`;


const TestPage = () => {

  const [data , setData] = useState([]);
  
  useEffect(() => {
      axios.get().then(Response => {
      setData(Response.data);
      console.log(Response.data);
  }).catch((Error)=> {
      console.log(Error) ;
  })
      }, [])

  return (
      <>
      {data.length}
    {data && 
     <Wrapper>
     <HorizonLine />
     <Wrapper_2>
       <i className="usericon fa-solid fa-circle-user"></i>
       <ContentText>{data.content}</ContentText>
     </Wrapper_2>
     <Wrapper_3>
       <HButton />
     </Wrapper_3>
   </Wrapper>
    
    }
      </>
  );
};

export default TestPage;