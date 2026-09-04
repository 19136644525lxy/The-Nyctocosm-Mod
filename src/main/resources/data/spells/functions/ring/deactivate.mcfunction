#built using mc-build (https://github.com/mc-build/mc-build)

item modify entity @s weapon.offhand spells:planering/deactivate
execute store result score TEMP UUID0 run data get entity @s UUID[0]
execute store result score TEMP UUID1 run data get entity @s UUID[1]
execute store result score TEMP UUID2 run data get entity @s UUID[2]
execute store result score TEMP UUID3 run data get entity @s UUID[3]
execute as @e[tag=ring,tag=plane] run function spells:ring/__generated__/block/540