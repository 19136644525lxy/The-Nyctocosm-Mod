#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove aj.aj_cast_spell.anim.cast_spell
scoreboard players set @s aj.frame 0
scoreboard players operation .oldValue aj.i = .noScripts aj.i
scoreboard players set .noScripts aj.i 1
function aj_cast_spell:animations/cast_spell/next_frame
scoreboard players operation .noScripts aj.i = .oldValue aj.i
scoreboard players set @s aj.frame 0
scoreboard players set #execute LANG_MC_INTERNAL 1