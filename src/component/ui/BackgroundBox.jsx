import React from 'react';
import styled from 'styled-components';

const TemplateBlock = styled.div`
text-align: center;
font-size: 20px;
font-weight: 700;
border : 5px solid;
border-radius: 50px;
border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
width: 381px;
height: 810px;
margin: auto;
color: #940F00;
background-color: white;
margin-top:25%;
`;



function Template({ children }) {
    return <TemplateBlock>{children}</TemplateBlock>;
  }
  
  export default Template;
