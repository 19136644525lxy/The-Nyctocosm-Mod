#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @e[tag=emissary,tag=emissarymodel,limit=1] m.damage_dealt.dissonance += damageAmt value
execute store result score DamHealthTemp value run data get entity @s Health
execute if score DamHealthTemp value matches 0 run function emissary:attacks/phase1/meteor_shower/__generated__/block/70
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation m.TEMP m.dissonance.tracechance = temp0 rng
scoreboard players operation m.TEMP m.dissonance.tracechance /= #100000 rng
scoreboard players operation m.TEMP m.dissonance.tracechance %= #100 rng
scoreboard players add m.TEMP m.dissonance.tracechance 0
scoreboard players operation m.TEMP2 value = @e[tag=emissary,tag=emissarymodel,limit=1] m.dissonance.tracechance
scoreboard players operation m.TEMP2 value += @e[tag=emissary,tag=emissarymodel,limit=1] m.global.tracechance
execute if score m.TEMP2 value matches 100.. run scoreboard players set m.TEMP2 value 100
execute if score m.TEMP m.dissonance.tracechance <= m.TEMP2 value as @e[tag=emissary,tag=emissarymodel,limit=1] run function magic:hypertrace/summon