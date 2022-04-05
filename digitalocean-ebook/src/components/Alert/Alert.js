import PropTypes from 'prop-types';
import './Alert.css';

export default function Alert({ children, title, type }) {
  return (
    <div className={`alert-wrapper ${type}`}>
      <h2>
        {title}
        {' '}
        {type}
      </h2>
      {children}
    </div>
  );
}

Alert.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.element),
    PropTypes.element.isRequired,
  ]).isRequired,
  title: PropTypes.string.isRequired,
  type: PropTypes.string.isRequired,
};
