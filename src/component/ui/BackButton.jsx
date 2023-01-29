import React from "react";
import "./backbutton.css";

function BackButton(props) {
  const { title, onClick } = props;
  return (
    <button className="styledbutton" onClick={onClick}>
      <span className="button__icon">
        <i className={props.icon} />
      </span>
    </button>
  );
}

export default BackButton;