#!/usr/bin/python

# dictionaries to strings using json
import json

# to send requests to server
import requests

# for command-line arguments
import sys

# all the crypto stuff is here
import tommieutils as tu

import time
import copy
# an empty Pay Transaction.
emptyPayTrans = {
    'transtype' : '1',
    'ins' : [],
    'outs' : [],
    'sigs' : []
}

# only valid if prevmine is id of block with the most recent mined transaction
# SHA256(nonce||SHA256(trans with nonce set to '')) should be hex that starts with six 0s
emptyMinedTrans = {
    'prevmine' : '',
    'transtype' : '2',
    'outs': [],
    'nonce' : '',
    'timestamp':''
}

######### YOUR CODE HERE ############
def findLastMined(chain):
    """
    return the block index/id containing most recent mined transaction
    used by isValidMinedTrans, since a valid mined transaction must have id of previous mined trans.
    """
    numblocks = len(chain['blocks'])
    i = numblocks-1
    while (i>=0):
        if (chain['blocks'][i]['transactions']['transtype']=='2'):
            break
        i -= 1
    return i

def isAvailCoin(inpair, chain):
    #coin (repr. by a pair (blocknum,outnum)) is available if it has never appeared in an ins list
    used = getAllUsedCoins(chain)
    if (inpair in used):
        return False
    return True

def getAllUsedCoins(chain):
    used = []
    for block in chain['blocks']:
        currtrans = block['transactions']
        if (currtrans['transtype']=='1'):
            currins = [(x['transid'],x['outnum']) for x in currtrans['ins']]
            used.extend(currins)
    return used

def orderMax(pk, chain):
    orderList = []
    for blocks in chain['blocks']:
        trans = blocks['transactions']
        count = 0
        for outs in trans['outs']:
            if(outs['recipient'] == tu.sha256(pk) and isAvailCoin((int(blocks['id']),count),chain)):
                blockId = blocks['id']
                orderList.append({'id':blockId, 'value':int(outs['value'])})
            count = count + 1
    
    orderList.sort(key= lambda x : x.get('value'), reverse=True)
    return orderList
                
def numberOfCoins(pk):
    r = requests.get('http://127.0.0.1:8080/showchainraw')
    chain = json.loads(r.text)
    total = 0
    for block in chain['blocks']:
        currentTrans = block['transactions']
        if(currentTrans['transtype'] == '0'):
            count = 0
            for outs in currentTrans['outs']:
                if(outs['recipient'] == tu.sha256(pk) and isAvailCoin((int(block['id']), count), chain)):
                    total = total + int(outs['value'])
                count = count + 1
        elif(currentTrans['transtype'] == '1'):
            count = 0
            for outs in currentTrans['outs']:
                if(outs['recipient'] == tu.sha256(pk) and isAvailCoin((int(block['id']), count), chain)):
                    if(count == 0):
                        total = total + int(outs['value'])
                    if(count == 1):
                        total = total + int(outs['value'])
                count = count + 1
        elif(currentTrans['transtype'] == '2'):
            for outs in currentTrans['outs']:
                if(outs['recipient'] == tu.sha256(pk) and isAvailCoin((int(block['id']), 0), chain)):
                    amount = outs['value']
                    total = total + int(amount)
    return total


def wantPay(pk, sk, name, amount):
    payName = name.lower()
    f = open('./keys/'+payName+'pk.txt', 'r')
    pkPay = f.read()
    f.close()
    myblock = copy.deepcopy(emptyPayTrans)
    r = requests.get('http://127.0.0.1:8080/showchainraw')
    chain = json.loads(r.text)
    highToLow = orderMax(pk, chain)
    saveValue = 0
    value = 0
    usedId = []
    print('Building Transaction....')
    for idblock in highToLow:
        if(saveValue >= int(amount)):
            break
        count = 0
        curBlock = chain['blocks'][int(idblock['id'])-1]
        trans = curBlock['transactions']
        count = 0
        for outs in trans['outs']:
            if(outs['recipient'] == tu.sha256(pk) and isAvailCoin((int(curBlock['id']),count),chain)):
                value = int(outs['value'])
                saveValue = int(outs['value']) + saveValue
                blockId = curBlock['id']
                myblock['ins'].append({'transid':int(blockId), 'outnum':count})
            count = count + 1
    returnVal = int(saveValue) - int(amount)
    myblock['outs'].append({'recipient':tu.sha256(pkPay), 'value':int(amount)})
    myblock['outs'].append({'recipient':tu.sha256(pk), 'value':returnVal})
    signature = tu.sign(sk,json.dumps(myblock, sort_keys=True))
    myblock['sigs'].append({'pk':pk, 'signature':signature})
    blockString = json.dumps(myblock, sort_keys=True)
    try:
        requests.post('http://127.0.0.1:8080/addtrans', data={'trans':blockString})
    except:
        print('failed')
        return

    print('Success')
    print('You curently have ' + str(numberOfCoins(pk)) + ' TommieCoins')



def wantMine(pk):
    block = copy.deepcopy(emptyMinedTrans)
    r = requests.get('http://127.0.0.1:8080/showchainraw')
    chain = json.loads(r.text)
    pkHash = tu.sha256(pk)
    prevIndex = str(findLastMined(chain))
    block['prevmine'] = prevIndex
    block['timestamp'] = str(time.time())
    block['outs'].append({'value':50, 'recipient':pkHash})
    hashNoNonce = tu.sha256(json.dumps(block, sort_keys=True))
    a = 5
    b = 10
    nonce = 0
    print("Mining Coins.....")
    while a < b:
        hashWithNonce = tu.sha256(str(nonce)+hashNoNonce)
        if(hashWithNonce[:6] == '000000'):
            break
        nonce = nonce + 1
    block['nonce'] = str(nonce)
    blockString = json.dumps(block, sort_keys=True)
    try:
        requests.post('http://127.0.0.1:8080/addtrans', data={'trans':blockString})
    except:
        print('failed')
        return

    print('Success')
    print('You curently have ' + str(numberOfCoins(pk)) + ' TommieCoins')







f = open('./keys/'+sys.argv[1], 'r')
pk = f.read()
f.close()
f = open('./keys/'+sys.argv[2], 'r')
sk = f.read()
f.close
pkName = sys.argv[1]
name = ""
for x in pkName:
    if(x == "p"):
        break
    name = name + x
print("Welcome, " + name.swapcase())
numOfCoins =  numberOfCoins(pk)
print("You currently have " + str(numOfCoins) + " TommieCoins")
b = 10
a = 0
userWant = ""
nameList = ["Alice", "Bob", "Carlos", "Dawn", "Eve"]
while a < b:
    userWant = raw_input("What would you like to do (pay, mine, quit): ")
    if(userWant == "pay"):
        printNames= ''
        for i in range(len(nameList)):
            if(nameList[i].lower() != name):
                if(i == len(nameList) - 1):
                    printNames = printNames + nameList[i]
                else:
                    printNames = printNames + nameList[i] + ", "
        print(printNames)
        userName = raw_input("Who would you like to pay: ")
        if(userName not in nameList):
            print("Name is not in list")
            continue
        userAmount = raw_input("How much would you like to pay (must be an integer): ")
        try:
            int(userAmount)
        except:
            print("No decimals!")
            continue
        if(int(userAmount) >= int(numOfCoins)):
            print("You don't have enough coins")
            continue
        wantPay(pk, sk, userName, userAmount)
    elif(userWant == "mine"):
        wantMine(pk)
    elif(userWant == "quit"):
        break
    else:
        print("Invalid option")




