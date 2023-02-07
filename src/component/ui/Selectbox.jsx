import React from 'react';
import styled from 'styled-components';


const TemplateBlock = styled.div`
text-align: center;
font-size: 25px;
font-weight: 700;
border : 5px solid;
border-radius: 50px;
border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;


width: 280.35px;
height: 280.35px;
margin: 0 auto;
color: #940F00;
background-color: white;
margin-bottom:1%;
margin-top: 15%;

`;



function Template({ children }) {
    return <TemplateBlock>{children}</TemplateBlock>;
  }
  
  export default Template;