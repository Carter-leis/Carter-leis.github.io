# TommieCoin

## Overview
Tommie coin is a project that was to model what a crypto currency was and the coding behind it. We had to create different transactions that the user could use. The transactions were: pay, mine, and show balance. The mine transaction was by far the best part, we had to write code that would solve a puzzle before another user to create more coins.

## Tommieserver.py
This is a python bottle server that maintains the TommieCoin blockchain. This was written by Scott Yilek for us to use to create our different transactions.

## Tommieutils.py
This is useful crypto functions for using in TommieCoin server and wallet apps. This was written by Scott Yilek for us to use to create our different transactions.

## Transactions.py
This is useful functions for handling blocks and transactions in TommieCoin. This was written by Scott Yilek for us to use to create our different transactions.

## Wallet.py
This is where the three transactions that we had to create are. In this file is the mining, payment, and show balance methods. To be able to do these methods the code had to be able to read the block chain and get values from the blockchain to be used in the different methods. It also had to be able to create a new transaction that the block chain would accept and store. 

## Currchain.txt
This is the blockchain of tommiecoin. It has all the previous transactions written in the file and who ones each block and how much each block is worth.

## Keys folder
This folder holds all of the private and secret keys of each individual. These are used when creating a new transaction on the blockchain
