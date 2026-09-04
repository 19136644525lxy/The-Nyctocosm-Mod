execute if score @s admg matches 5120.. run function damage:damage/values/512
execute if score @s admg matches 2560.. run function damage:damage/values/256
execute if score @s admg matches 1280.. run function damage:damage/values/128
execute if score @s admg matches 640.. run function damage:damage/values/64
execute if score @s admg matches 320.. run function damage:damage/values/32
execute if score @s admg matches 160.. run function damage:damage/values/16
execute if score @s admg matches 80.. run function damage:damage/values/8
execute if score @s admg matches 40.. run function damage:damage/values/4
execute if score @s admg matches 20.. run function damage:damage/values/2
execute if score @s admg matches 10.. run function damage:damage/values/1
execute if score @s admg matches ..9 run function damage:damage/values/dec
damage @s 0.00001 minecraft:generic by @e[tag=damager,limit=1]