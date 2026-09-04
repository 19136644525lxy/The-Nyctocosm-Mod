#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["tonysummoner"]}
execute as @e[tag=tonysummoner] at @s run function nyctocosm:tony/__generated__/block/118
advancement revoke @s only nyctocosm:tony/tonyspawn
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s tonytimer = temp0 rng
scoreboard players operation @s tonytimer /= #100000 rng
scoreboard players operation @s tonytimer %= #12000 rng
scoreboard players add @s tonytimer 12000