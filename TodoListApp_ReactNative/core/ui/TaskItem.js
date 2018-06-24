'use strict';

import React, { Component } from 'react';

import { StyleSheet, View,Text } from 'react-native';

export default class TaskItem extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <View style={styles.container}>
                <Text>
                    Item 1
                </Text>
            </View>
        )
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center'
    }
});