#built using mc-build (https://github.com/mc-build/mc-build)

function magic:calcstatsspell
scoreboard players operation 0 temp = @s m.dream.power
scoreboard players operation 0 temp *= #100 const
scoreboard players add 0 temp 500
scoreboard players operation @s s.duration = 0 temp
scoreboard players operation TEMP s.duration = @s s.duration
scoreboard players reset @s s.duration
function spells:assignplaneid
execute at @s positioned ~ ~ ~ summon marker run function spells:ring/__generated__/block/537
execute at @s run playsound nyctocosm:spells.cast.magic_plane master @a[distance=..10]
execute at @e[tag=spell,tag=origin,distance=..2,sort=nearest,limit=1] rotated ~ 0 positioned ~ ~ ~ run function effects:shockwave/spellplane
execute at @s positioned ~ ~ ~ run function spells:ring/__generated__/block/538