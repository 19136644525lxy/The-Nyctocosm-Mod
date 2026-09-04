#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation SerpentPassiveTimer value = temp0 rng
scoreboard players operation SerpentPassiveTimer value /= #100000 rng
scoreboard players operation SerpentPassiveTimer value %= #30 rng
scoreboard players add SerpentPassiveTimer value 20
execute as @e[tag=target,tag=serpent] run function serpent:__generated__/block/195