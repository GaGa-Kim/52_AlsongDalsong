import React from 'react';
import styled from 'styled-components';


const TemplateBlock = styled.div`
text-align: center;
position: relative; 
background-color : #FA0050;

margin: 0 auto; /* 페이지 중앙에 나타나도록 설정 */

display: flex;
flex-direction: column;
padding-bottom: 60px;
`;

function Template({ children }) {
    return <TemplateBlock>{children}</TemplateBlock>;
  }
  
  export default Template;