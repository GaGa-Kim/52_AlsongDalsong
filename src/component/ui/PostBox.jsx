import React from 'react';
import styled from 'styled-components';


const TemplateBlock = styled.div`
    width: calc(100% - 32px);
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    border-color:transparent;
    background: #F9F9F9;
   
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 30px;
`;



function Template({ children }) {
    return <TemplateBlock>{children}</TemplateBlock>;
  }
  
export default Template;