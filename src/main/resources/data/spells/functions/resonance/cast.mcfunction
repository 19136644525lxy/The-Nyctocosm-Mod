#built using mc-build (https://github.com/mc-build/mc-build)

function magic:calcstatsspell
scoreboard players set 100 value 100
scoreboard players operation @s m.spell.cast_time = Resonance s.cast_time
scoreboard players operation @s m.spell.power = Resonance s.power
function spells:resonance/__generated__/block/235
scoreboard players operation m.TEMP2 value = @s m.global.tracechance
execute if score Resonance s.type1 matches 1 run function spells:resonance/__generated__/block/236
execute if score Resonance s.type2 matches 1 run function spells:resonance/__generated__/block/238
execute if score Resonance s.type3 matches 1 run function spells:resonance/__generated__/block/240
execute if score Resonance s.type4 matches 1 run function spells:resonance/__generated__/block/242
execute if score Resonance s.type5 matches 1 run function spells:resonance/__generated__/block/244
execute if score Resonance s.type6 matches 1 run function spells:resonance/__generated__/block/246
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation m.TEMP m.global.tracechance = temp0 rng
scoreboard players operation m.TEMP m.global.tracechance /= #100000 rng
scoreboard players operation m.TEMP m.global.tracechance %= #100 rng
scoreboard players add m.TEMP m.global.tracechance 0
execute if score m.TEMP2 value matches 100.. run scoreboard players set m.TEMP2 value 100
execute if score m.TEMP m.global.tracechance <= m.TEMP2 value as @s at @s positioned ^ ^ ^3 run function magic:hypertrace/summon
scoreboard players reset 100 value
tag @s remove tellrawed
scoreboard players set @s[scores={m.spell.power=50..}] m.spell.power 50
scoreboard players operation SpellTempStat m.spell.cast_time = @s m.spell.cast_time
scoreboard players operation SpellTempStat m.spell.power = @s m.spell.power
data modify entity @e[tag=spell,tag=origin,tag=plane,sort=nearest,limit=1] data.UUID set from entity @s UUID
execute at @s as @e[tag=spell,tag=origin,tag=plane,sort=nearest,limit=1] run function spells:resonance/__generated__/block/248
particle minecraft:flash ~ ~1 ~ 0.2 0.2 0.2 0 5
tag @s add caster
execute positioned ~ ~1.625 ~ positioned ^ ^ ^5 run summon marker ~ ~ ~ {Tags:["spell","Resonance","shooter","justsummoned"]}
scoreboard players operation SpellTemp m.spell.cast_time = @s m.spell.cast_time
scoreboard players operation SpellTemp m.spell.cast_time = @s m.spell.cast_time
execute as @e[tag=justsummoned,tag=shooter] at @s run function spells:resonance/__generated__/block/250
execute as @e[tag=spell,tag=origin,limit=1,distance=..1,sort=nearest] at @s run function spells:resonance/__generated__/block/251
tag @s remove caster