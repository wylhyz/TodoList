'use strict';

import React, { Component } from 'react';

import { FlatList, StyleSheet, View } from 'react-native';


export default class TaskList extends Component {

    _renderItem(item) {
        return (
            <TaskItem />
        );
    }

    render() {
        return (
            <View style={styles.container}>
                <FlatList
                    style={{ flex: 1 }}
                    renderItem={this._renderItem} />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    }
});