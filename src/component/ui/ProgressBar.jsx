import React from 'react'
import "./progressbar.css";

export default function ProgressBar({done}) {
    const[style, setStyle] = React.useState({});
    setTimeout(()=> {
        const newStyle = {
            opacity: 1,
            width: `${done}%`
        }
        setStyle(newStyle);
    },100)
    return (
    <div className='progress'>
        <div className='progress-done'style={style}>
        {done}%
      </div>
    </div>
  )
}
