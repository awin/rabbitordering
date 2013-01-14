# Purpose

This is a showcase project for [RabbitEasy](https://github.com/zanox/rabbiteasy).

# Prerequisites

Make sure you have the latest version of [RabbitMQ](http://rabbitmq.com) installed on your machine and that it is available
under your localhost with default settings.

# Usage

1. Start OrderProcessConsole in rabbitordering-client
2. Start OrderStatisticsConsole in rabbitordering-statistics
3. Experiment with the listed console commands

Publishing event of type OrderCreatedEvent
Publishing message to exchange 'com.zanox.rabbitordering' with routing key 'com.zanox.rabbitordering.order.created' (deliveryOptions: NONE, persistent: true)
Successfully published message to exchange 'com.zanox.rabbitordering' with routing key 'com.zanox.rabbitordering.order.created'
Waiting for publisher ack
Received publisher ack
Successfully published event of type OrderCreatedEvent
Order 966cc8bc-ff12-41bd-899a-d8333bae3b24 of 2 Gloves(s) for Steph Jobs created