#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s[scores={calmness=10..}] calmness 3
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s feartimer = temp0 rng
scoreboard players operation @s feartimer /= #100000 rng
scoreboard players operation @s feartimer %= #180 rng
scoreboard players add @s feartimer 20
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation FearEvent value = temp0 rng
scoreboard players operation FearEvent value /= #100000 rng
scoreboard players operation FearEvent value %= #3 rng
scoreboard players add FearEvent value 1
execute if score FearEvent value matches 1 run function nyctocosm:player/__generated__/block/8
execute if score FearEvent value matches 2 run function nyctocosm:player/__generated__/block/10
execute if score FearEvent value matches 3 run function nyctocosm:player/__generated__/block/12