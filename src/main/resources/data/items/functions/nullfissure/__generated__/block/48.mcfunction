#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @e[tag=damagetemp,limit=1] m.damage_dealt.generic += damageAmt value
execute store result score DamHealthTemp value run data get entity @s Health
execute if score DamHealthTemp value matches 0 run function items:nullfissure/__generated__/block/49
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation m.TEMP m.generic.tracechance = temp0 rng
scoreboard players operation m.TEMP m.generic.tracechance /= #100000 rng
scoreboard players operation m.TEMP m.generic.tracechance %= #100 rng
scoreboard players add m.TEMP m.generic.tracechance 0
scoreboard players operation m.TEMP2 value = @e[tag=damagetemp,limit=1] m.generic.tracechance
scoreboard players operation m.TEMP2 value += @e[tag=damagetemp,limit=1] m.global.tracechance
execute if score m.TEMP2 value matches 100.. run scoreboard players set m.TEMP2 value 100
execute if score m.TEMP m.generic.tracechance <= m.TEMP2 value as @e[tag=damagetemp,limit=1] run function magic:hypertrace/summon