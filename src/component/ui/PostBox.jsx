import React from 'react';
import styled from 'styled-components';


const TemplateBlock = styled.div`

    width: calc(100% - 32px);
    padding-top: 10px;
    padding-right: 16px;
    padding-bottom: 30px;
    padding-left: 16px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    border-color:transparent;

    background: #F9F9F9;
   
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 20px;

`;



function Template({ children }) {
    return <TemplateBlock>{children}</TemplateBlock>;
  }
  
  export default Template;