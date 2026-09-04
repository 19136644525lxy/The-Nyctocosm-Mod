#built using mc-build (https://github.com/mc-build/mc-build)

execute if score @s admg matches 5120.. run function damage:damage_dream/__generated__/block/1
execute if score @s admg matches 2560.. run function damage:damage_dream/__generated__/block/2
execute if score @s admg matches 1280.. run function damage:damage_dream/__generated__/block/3
execute if score @s admg matches 640.. run function damage:damage_dream/__generated__/block/4
execute if score @s admg matches 320.. run function damage:damage_dream/__generated__/block/5
execute if score @s admg matches 160.. run function damage:damage_dream/__generated__/block/6
execute if score @s admg matches 80.. run function damage:damage_dream/__generated__/block/7
execute if score @s admg matches 40.. run function damage:damage_dream/__generated__/block/8
execute if score @s admg matches 20.. run function damage:damage_dream/__generated__/block/9
execute if score @s admg matches 10.. run function damage:damage_dream/__generated__/block/10
execute if score @s admg matches ..9 run function damage:damage_dream/__generated__/block/11
damage @s 0.00001 minecraft:generic by @e[tag=damager,limit=1]