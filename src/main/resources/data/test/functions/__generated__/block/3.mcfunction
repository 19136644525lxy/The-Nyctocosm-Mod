#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s m.damage_dealt.dream += damageAmt value
execute store result score DamHealthTemp value run data get entity @s Health
execute if score DamHealthTemp value matches 0 run function test:__generated__/block/4
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation m.TEMP m.dream.tracechance = temp0 rng
scoreboard players operation m.TEMP m.dream.tracechance /= #100000 rng
scoreboard players operation m.TEMP m.dream.tracechance %= #100 rng
scoreboard players add m.TEMP m.dream.tracechance 0
scoreboard players operation m.TEMP2 value = @s m.dream.tracechance
scoreboard players operation m.TEMP2 value += @s m.global.tracechance
execute if score m.TEMP2 value matches 100.. run scoreboard players set m.TEMP2 value 100
execute if score m.TEMP m.dream.tracechance <= m.TEMP2 value as @s run function magic:hypertrace/summon