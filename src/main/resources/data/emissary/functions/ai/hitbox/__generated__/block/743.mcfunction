#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if predicate emissary:hurtscreamchance run function emissary:ai/hitbox/__generated__/conditional/10
execute if score #execute LANG_MC_INTERNAL matches 0 run function emissary:ai/hitbox/__generated__/conditional/11