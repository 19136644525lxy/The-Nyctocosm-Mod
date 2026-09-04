#built using mc-build (https://github.com/mc-build/mc-build)

function magic:calcstatsspell
scoreboard players set 100 value 100
scoreboard players operation @s m.spell.cast_time = Incurrence s.cast_time
scoreboard players operation @s m.spell.power = Incurrence s.power
function spells:incurrence/__generated__/block/280
scoreboard players operation m.TEMP2 value = @s m.global.tracechance
execute if score Incurrence s.type1 matches 1 run function spells:incurrence/__generated__/block/281
execute if score Incurrence s.type2 matches 1 run function spells:incurrence/__generated__/block/283
execute if score Incurrence s.type3 matches 1 run function spells:incurrence/__generated__/block/285
execute if score Incurrence s.type4 matches 1 run function spells:incurrence/__generated__/block/287
execute if score Incurrence s.type5 matches 1 run function spells:incurrence/__generated__/block/289
execute if score Incurrence s.type6 matches 1 run function spells:incurrence/__generated__/block/291
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
execute at @s as @e[tag=spell,tag=origin,tag=plane,sort=nearest,limit=1] run function spells:incurrence/__generated__/block/293
execute as @e[tag=spell,tag=origin,limit=1,distance=..1,sort=nearest] at @s run function spells:incurrence/__generated__/block/295
particle minecraft:flash ~ ~1 ~ 0.2 0.2 0.2 0 5