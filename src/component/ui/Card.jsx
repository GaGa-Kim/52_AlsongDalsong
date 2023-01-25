import Card from 'react-bootstrap/Card';
import CardGroup from 'react-bootstrap/CardGroup';
import MyPageBT from '../ui/MyPageBT';
import './card.css';

function GroupExample({title, select, image}) {
  return (
    <CardGroup className="frame">
      <Card className="card">
        <Card.Title><div className='catebox'>{select}</div></Card.Title>
        <Card.Img variant="top" src={image} className="img" />
        <Card.Body>
          <Card.Title className="font">{title}</Card.Title>
          <Card.Text>
                <MyPageBT className=""/>
          </Card.Text>
        </Card.Body>
      </Card>
    </CardGroup>
  );
}

export default GroupExample;