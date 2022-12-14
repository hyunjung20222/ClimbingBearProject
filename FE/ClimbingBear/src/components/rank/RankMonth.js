import React, {useState} from 'react';
import {View, StyleSheet, Dimensions} from 'react-native';
import {
  TextLight,
  TextMedium,
  TextBold,
  TextExtraBold,
} from '../../components/common/TextFont';
import DropDownPicker from 'react-native-dropdown-picker';

const windowWidth = Dimensions.get('window').width;
const windowHeight = Dimensions.get('window').height;

const RankMonth = ({month, setMonth}) => {
  const [monthOpen, setMonthOpen] = useState(false);
  const [monthItems, setMonthItems] = useState([
    {label: '1', value: '1'},
    {label: '2', value: '2'},
    {label: '3', value: '3'},
    {label: '4', value: '4'},
    {label: '5', value: '5'},
    {label: '6', value: '6'},
    {label: '7', value: '7'},
    {label: '8', value: '8'},
    {label: '9', value: '9'},
    {label: '10', value: '10'},
    {label: '11', value: '11'},
    {label: '12', value: '12'},
  ]);

  return (
    <View style={styles.monthPicker}>
      <DropDownPicker
        open={monthOpen}
        value={month}
        items={monthItems}
        setOpen={setMonthOpen}
        setValue={setMonth}
        setItems={setMonthItems}
        maxHeight={500}
        onChangeItem={item => setValue(item.value)}
        textStyle={{
          fontSize: 11,
          fontFamily: 'SeoulNamsanM',
          alignItems: 'center',
        }}
      />
    </View>
  );
};

export default RankMonth;

const styles = StyleSheet.create({
  monthPicker: {
    width: windowWidth * 0.3,
    margin: 10,
  },
});
