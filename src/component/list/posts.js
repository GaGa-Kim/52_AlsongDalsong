import React, { useEffect, useState } from 'react';
import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";
import HButton from "../ui/HeartButton";
import axios from 'axios';

const Wrapper = styled.div`
  width: 340px;
  padding: 0px 16px;
  margin-bottom:7px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-family: 'GmarketSansTTFMedium';
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
  justify-content: space-beteween;

`;
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
  font-family: 'GmarketSansTTFMedium';
  line-height: 1.5;
  color:#000000;
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
          'http://35.216.20.124:8080/api/comment/inquire?postId=1'
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
            <HorizonLine />
            <Wrapper_2>
              <i className="usericon fa-solid fa-circle-user"></i>
              <ContentText>
                {user.nickname}<br/> {user.content} 
              </ContentText>
            </Wrapper_2>
            <Wrapper_3>
              <HButton />
            </Wrapper_3>
          </Wrapper>
        </li>
      ))}
    </ul>
  );
}

export default Users;