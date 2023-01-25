import Card from 'react-bootstrap/Card';
import CardGroup from 'react-bootstrap/CardGroup';
import './CardSC.css';

function GroupExample({title, select, image}) {
  return (
    <CardGroup className="frame2">
      <Card className="card2">
        <Card.Title><div className='catebox'>{select}</div></Card.Title>
        <Card.Img variant="top" src={image} className="img" />
        <Card.Body>
          <Card.Title className="font">{title}</Card.Title>
        </Card.Body>
      </Card>
    </CardGroup>
  );
}

export default GroupExample;