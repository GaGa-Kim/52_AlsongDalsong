import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";
import Likes from "../ui/likes";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Wrapper = styled.div`
  width: calc(100% - 32px);
  padding-top: 10px;
  padding-right: 16px;
  padding-bottom: 10px;
  padding-left: 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  border-color: transparent;
  border-radius: 20px;
  cursor: pointer;
  background: #efefef;
  :hover {
    background: lightgrey;
  }
  box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.25);

  .DecisionIcon {
    font-size: 30px;
    position: absolute;
    margin-left: 75%;
    margin-top: -40%;
    color: #fa0050;
  }
`;
const NameandDay = styled.p`
  position: absolute;
  width: 280px;
  height: 40px;
  font-family: "Inter";
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  line-height: 16px;
`;

const TitleText = styled.p`
  width: 280px;
  height: 5px;
  font-family: "Inter";
  font-style: normal;
  font-weight: 600;
  font-size: 17px;
  font-family: 'GmarketSansTTFMedium';
  text-align: left;
  padding-top: 10px;
  color: #000000;
  
`;
const ContentText = styled.p`
  white-space: pre-wrap;
  font-family: "Inter";
  font-style: normal;
  font-weight: 500;
  font-size: 12px;
  font-family: 'GmarketSansTTFMedium';
  line-height: 15px;
  display: flex;
  text-align: left;
  padding-bottom: 20px;
  padding-top: 30px;
  color: #000000;
`;
const Space = styled.div`
  width: 15px;
  height: auto;
  display: inline-block;
`;


function Users() {
    const [users, setUsers] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
  
    useEffect(() => {
      const fetchUsers = async () => {
        try {
          // 요청이 시작 할 때에는 error 와 users 를 초기화하고
          setError(null);
          setUsers(null);
          // loading 상태를 true 로 바꿉니다.
          setLoading(true);
          const response = await axios.get(
      
          );
          setUsers(response.data); // 데이터는 response.data 안에 들어있습니다.
        } catch (e) {
          setError(e);
        }
        setLoading(false);
      };
  
      fetchUsers();
    }, []);
  
    if (loading) return <div>로딩중..</div>;
    if (error) return <div>에러가 발생했습니다</div>;
    if (!users) return null;
    return (
      <ul>
        {users.map(user => (
               <li key={user.id}>
           <Wrapper>
      <NameandDay></NameandDay>
      <TitleText>{user.what} 살까 말까</TitleText>
      <i className="DecisionIcon fa-regular fa-circle-check"></i>
      <ContentText>{user.content}</ContentText>
      <HorizonLine />
      <Likes />
    </Wrapper>
    <Space/>
             
     
          </li>
        ))}
      </ul>
    );
  }
  
  export default Users;