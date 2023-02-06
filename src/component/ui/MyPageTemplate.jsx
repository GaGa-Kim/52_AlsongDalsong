import React from 'react';
import styled from 'styled-components';


const TemplateBlock = styled.div`
width: 390px;
height: 1850px;
text-align: center;
position: relative; 
background-color : #FA0050;
border : 2px solid black;
border-radius: 16px;
box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.04);

margin: 0 auto; /* 페이지 중앙에 나타나도록 설정 */

margin-top: 30px;
margin-bottom: 32px;
display: flex;
flex-direction: column;
background-image: url(./img/mypage.jpg);
`;

function Template({ children }) {
    return <TemplateBlock>{children}</TemplateBlock>;
  }
  
  export default Template;