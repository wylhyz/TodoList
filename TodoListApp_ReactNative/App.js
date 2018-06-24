/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {StyleSheet, View} from 'react-native';

import TaskList from './core/ui/TaskList';

export default class App extends Component {
    render() {
        return (
            <View style={styles.container}>
                <TaskList/>
            </View>
        );
    }
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center'
    }
});