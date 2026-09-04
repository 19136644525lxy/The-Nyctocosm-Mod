#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s UUID0 run data get entity @s UUID[0]
execute store result score @s UUID1 run data get entity @s UUID[1]
execute store result score @s UUID2 run data get entity @s UUID[2]
execute store result score @s UUID3 run data get entity @s UUID[3]
execute if score @s UUID0 = UUID0 value if score @s UUID1 = UUID1 value if score @s UUID2 = UUID2 value if score @s UUID3 = UUID3 value run tag @s add UUIDMatch